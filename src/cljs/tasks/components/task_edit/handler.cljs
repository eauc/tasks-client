(ns tasks.components.task-edit.handler
  (:require [cljs.spec :as spec]
            [re-frame.core :as re-frame]
            [tasks.db :as db]
            [tasks.models.task :as task]
            [tasks.routes :as routes]))

(re-frame/reg-cofx
 :task-id
 (fn [coeffects _]
   (assoc coeffects :task-id (str (.now js/Date)))))

(re-frame/reg-event-fx
 :task-create-save
 db/default-interceptors
 (fn task-create-save [{:keys [db]} _]
   (let [task (:task-edit db)]
     (if (spec/valid? :tasks.specs.task/task task)
       {:db (-> db
                (update-in [:tasks-list :tasks] #(cons task %))
                (assoc-in [:task-edit] nil))
        :nav {:route routes/home}}
       {}))))

(re-frame/reg-event-fx
 :task-create-start
 [(re-frame/inject-cofx :task-id)
  db/default-interceptors]
  (fn task-create-start [{:keys [db task-id]} _]
    {:db (-> db
             (assoc-in [:task-edit] {:id task-id
                                     :title ""
                                     :body ""
                                     :done false})
             (assoc-in [:page] :task-create))}))

(re-frame/reg-event-fx
 :task-edit-save
 db/default-interceptors
 (fn task-edit-save [{:keys [db]} _]
   (let [task (:task-edit db)]
     (if (spec/valid? :tasks.specs.task/task task)
       {:db (-> db
                (update-in [:tasks-list :tasks] task/update-task task)
                (assoc-in [:task-edit] nil))
        :nav {:route routes/home}}
       {}))))

(re-frame/reg-event-fx
 :task-edit-start
 db/default-interceptors
 (fn task-edit-start [{:keys [db]} [_ id]]
   (let [tasks (get-in db [:tasks-list :tasks])
         task (first (filter #(= (:id %) id) tasks))]
     (if task
       {:db (-> db
                (assoc-in [:task-edit] task)
                (assoc-in [:page] :task-edit))
        :nav {:route routes/task-edit :params {:id (:id task)}}}
       {:nav {:route routes/home}}))))

(re-frame/reg-event-db
 :task-edit-update
 [db/default-interceptors
  (re-frame/path :task-edit)]
 (fn task-edit-update [db [_ field value]]
   (assoc-in db field value)))
