(ns tasks.components.tasks-list.handler
  (:require [ajax.core :as ajax]
            [re-frame.core :as re-frame]
            [day8.re-frame.http-fx]
            [tasks.db :as db]
            [tasks.debug :as debug :refer [debug?]]
            [tasks.models.tasks-list :as tasks-list]))

(re-frame/reg-event-db
 :tasks-filter-update
 [db/default-interceptors
  (re-frame/path :tasks-filter)]
 (fn tasks-filter-update [db [_ filter]]
   filter))

(re-frame/reg-event-db
 :tasks-toggle-details
 [db/default-interceptors
  (re-frame/path :tasks-show-details)]
 (fn tasks-toggle-details [db [_ tasks-show-details]]
   tasks-show-details))

(re-frame/reg-event-db
 :tasks-update
 [db/default-interceptors
  (re-frame/path :tasks-list :tasks)]
 (fn tasks-update [db [_ tasks]]
   tasks))

(re-frame/reg-event-fx
 :tasks-list-set-current
 [(re-frame/inject-cofx :storage-list)
  db/default-interceptors]
 (fn tasks-list-set-current [{:keys [db storage]} _]
   {:db (merge db storage)}))

(re-frame/reg-event-fx
 :tasks-list-delete
 [(re-frame/inject-cofx :storage-list "default")
  db/default-interceptors]
 (fn tasks-list-delete [{:keys [db storage]} [_ name]]
   (if (= name "default")
     {:db (assoc-in db [:tasks-list :tasks] [])}
     {:db (-> db
              (merge storage)
              (update-in [:tasks-lists-names] #(tasks-list/delete % name)))
      :storage-delete name})))

(re-frame/reg-event-db
 :tasks-list-delete-confirm
 db/default-interceptors
 (fn tasks-list-delete-confirm [db [_ list]]
   (let [name (:name list)]
     (assoc db :prompt {:type :confirm
                        :message (str "Are you sure you want to delete \"" name "\" list ?")
                        :value name
                        :on-validate [:tasks-list-delete]}))))

(def tasks-api
  (if debug?
    "http://localhost:4567"
    "https://eauc-tasks-data.herokuapp.com"))

(defn- online-create-tasks-list [tasks-list auth-token]
  {:method :post
   :uri (str tasks-api "/tasks/mine")
   :headers {:Authorization (str "Bearer " auth-token)}
   :params tasks-list
   :format (ajax/json-request-format)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:tasks-list-sync-success]
   :on-failure [:tasks-list-sync-failure]})

(defn- online-update-tasks-list [tasks-list auth-token]
  {:method :put
   :uri (str tasks-api "/tasks/mine/" (:id tasks-list))
   :headers {:Authorization (str "Bearer " auth-token)}
   :params tasks-list
   :format (ajax/json-request-format)
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:tasks-list-sync-success]
   :on-failure [:tasks-list-sync-failure]})

(defn- online-check-tasks-list [tasks-list auth-token]
  {:method :get
   :uri (str tasks-api "/tasks/mine/" (:id tasks-list))
   :headers {:Authorization (str "Bearer " auth-token)}
   :response-format (ajax/json-response-format {:keywords? true})
   :on-success [:tasks-list-sync-check]
   :on-failure [:tasks-list-sync-failure]})

(re-frame/reg-event-fx
 :tasks-list-sync
 (fn tasks-list-sync [{:keys [db]}]
   (let [tasks-list (:tasks-list db)
         create? (nil? (:id tasks-list))]
     {:db (assoc-in db [:auth :status] (if create?  "Creating..." "Syncing..."))
      :http-xhrio ((if create? online-create-tasks-list online-check-tasks-list)
                   tasks-list (get-in db [:auth :token]))})))

(re-frame/reg-event-fx
 :tasks-list-sync-check
 [db/default-interceptors]
 (fn tasks-list-sync [{:keys [db]} [_ response]]
   (let [local-date (js/Date. (get-in db [:tasks-list :updatedAt]))
         online-date (js/Date. (get-in response [:tasksList :updatedAt]))]
     (if (>= local-date online-date)
       {:db (assoc-in db [:auth :status] "Saving...")
        :http-xhrio (online-update-tasks-list
                     (:tasks-list db) (get-in db [:auth :token]))}
       {:db (-> db
                (assoc :tasks-list (:tasksList response))
                (update :auth dissoc :status))}))))

(re-frame/reg-event-db
 :tasks-list-sync-success
 [db/default-interceptors]
 (fn tasks-list-sync-success [db [_ response]]
   (-> db
       (assoc :tasks-list (:tasksList response))
       (update :auth dissoc :status))))

(re-frame/reg-event-db
 :tasks-list-sync-failure
 [db/default-interceptors]
 (fn tasks-list-sync-success [db [_ response]]
   (let [status (:status response)
         reset-token? (or (= 401 status) (= 403 status))
         reset-id? (= 404 status)]
     (-> db
         (update :auth dissoc :status)
         (update :auth #(if reset-token? (dissoc % :token) %))
         (update :tasks-list #(if reset-id? (dissoc % :id :updatedAt :user) %))))))
