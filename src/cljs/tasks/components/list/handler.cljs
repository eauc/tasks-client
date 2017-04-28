(ns tasks.components.list.handler
  (:require [re-frame.core :as re-frame]
            [tasks.db :as db]
            [tasks.debug :as debug :refer [debug?]]
            [tasks.models.list :as list-model]
            [tasks.routes :as routes]
            [cljs.spec :as spec]))

(def interceptors
  [db/check-spec-interceptor
   (when debug? re-frame/debug)])

(re-frame/reg-event-fx
 :list-create-start
 interceptors
 (fn list-create-start [{:keys [db]} _]
   {:db (-> db
            (assoc :list-edit {:new-name ""
                               :current-name nil})
            (assoc :page :list-create))}))

(re-frame/reg-event-fx
 :list-create
 interceptors
 (fn list-create [{:keys [db]} _]
   (let [new-name (get-in db [:list-edit :new-name])]
     (if (spec/valid? :tasks.models.list/name new-name)
       {:db (-> db
                (assoc :list-edit nil)
                (assoc :current-list new-name)
                (update-in [:lists] #(list-model/create % new-name))
                (assoc :tasks []))
        :nav {:route routes/home}}
       {}))))

(re-frame/reg-event-fx
 :list-current
 [(re-frame/inject-cofx :storage-list)
  interceptors]
 (fn current-list [{:keys [db storage]} [_ list]]
   {:db (merge db storage)}))

(re-frame/reg-event-fx
 :list-delete
 [(re-frame/inject-cofx :storage-list "default")
  interceptors]
 (fn current-list [{:keys [db storage]} [_ list]]
   (if (= list "default")
     {:db (assoc db :tasks [])}
     {:db (-> db
              (merge storage)
              (update-in [:lists] #(list-model/delete % list)))
      :storage-delete list})))

(re-frame/reg-event-fx
 :list-edit-start
 interceptors
 (fn list-edit-start [{:keys [db]} [_ id]]
   {:db (-> db
            (assoc :list-edit {:new-name id
                               :current-name id})
            (assoc :page :list-edit))}))

(re-frame/reg-event-db
 :list-edit-update
 [interceptors
  (re-frame/path :list-edit)]
 (fn list-edit-update [db [_ field value]]
   (assoc-in db field value)))

(re-frame/reg-event-fx
 :list-save
 interceptors
 (fn list-save [{:keys [db]} _]
   (let [new-name (get-in db [:list-edit :new-name])
         current-name (get-in db [:list-edit :current-name])]
     {:db (-> db
              (assoc :list-edit nil)
              (assoc :current-list new-name)
              (update-in [:lists]
                         #(list-model/rename % current-name new-name)))
      :nav {:route routes/home}
      :storage-delete current-name})))
