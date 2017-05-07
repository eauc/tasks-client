(ns tasks.components.tasks-list.handler
  (:require [re-frame.core :as re-frame]
            [tasks.db :as db]
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
