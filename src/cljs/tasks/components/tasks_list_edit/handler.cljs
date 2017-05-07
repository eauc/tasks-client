(ns tasks.components.tasks-list-edit.handler
  (:require [re-frame.core :as re-frame]
            [tasks.db :as db]
            [tasks.models.tasks-list :as tasks-list]
            [tasks.models.tasks-list-edit :as tasks-list-edit]
            [tasks.routes :as routes]))

(re-frame/reg-event-fx
 :tasks-list-create-save
 db/default-interceptors
 (fn list-create [{:keys [db]} _]
   (let [edit (:tasks-list-edit db)
         lists-names (:tasks-lists-names db)
         new-name (:new-name edit)]
     (if (empty? (tasks-list-edit/describe-errors edit lists-names))
       {:db (-> db
                (assoc :tasks-list-edit nil)
                (assoc-in [:tasks-list] {:name new-name
                                         :id nil
                                         :tasks []})
                (update-in [:tasks-lists-names] #(tasks-list/create % new-name)))
        :nav {:route routes/home}}
       {}))))

(re-frame/reg-event-fx
 :tasks-list-create-start
 db/default-interceptors
 (fn list-create-start [{:keys [db]} _]
   {:db (-> db
            (assoc :tasks-list-edit {:new-name ""
                                    :current-name nil})
            (assoc :page :tasks-list-create))}))

(re-frame/reg-event-fx
 :tasks-list-edit-save
 db/default-interceptors
 (fn tasks-list-edit-save [{:keys [db]} _]
   (let [edit (:tasks-list-edit db)
         lists-names (:tasks-lists-names db)
         current-name (:current-name edit)
         new-name (:new-name edit)]
     (if (empty? (tasks-list-edit/describe-errors edit lists-names))
       {:db (-> db
                (assoc :tasks-list-edit nil)
                (assoc-in [:tasks-list :name] new-name)
                (update-in [:tasks-lists-names]
                           #(tasks-list/rename % current-name new-name)))
        :nav {:route routes/home}
        :storage-delete current-name}
       {}))))

(re-frame/reg-event-fx
 :tasks-list-edit-start
 db/default-interceptors
 (fn list-edit-start [{:keys [db]} [_ name]]
   {:db (-> db
            (assoc :tasks-list-edit {:new-name name
                                     :current-name name})
            (assoc :page :tasks-list-edit))}))

(re-frame/reg-event-db
 :tasks-list-edit-update
 [db/default-interceptors
  (re-frame/path :tasks-list-edit)]
 (fn tasks-list-edit-update [db [_ field value]]
   (assoc-in db field value)))
