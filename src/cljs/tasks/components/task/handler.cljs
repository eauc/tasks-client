(ns tasks.components.task.handler
  (:require [re-frame.core :as re-frame]
            [tasks.models.tasks :as tasks]
            [tasks.routes :as routes]))

(re-frame/reg-event-db
 :create-save
 (fn [db [_ task]]
   (-> db
       (update-in [:tasks] #(cons task %))
       (assoc-in [:edit] nil))))

(re-frame/reg-event-db
 :create-start
 (fn [db _]
   (.log js/console "create-start" db)
   (-> db
       (assoc-in [:edit] {:id (str (.now js/Date))
                          :title ""
                          :body ""
                          :done false})
       (assoc-in [:page] :create))))

(re-frame/reg-event-db
 :edit-update
 (fn [db [_ edit]]
   (assoc-in db [:edit] edit)))

(re-frame/reg-event-db
 :edit-save
 (fn [db [_ task]]
   (-> db
       (update-in [:tasks] tasks/update-task task)
       (assoc-in [:edit] nil))))

(re-frame/reg-event-db
 :edit-start
 (fn [db [_ id]]
   (let [tasks (:tasks db)
         task (first (filter #(= (:id %) id) tasks))]
     (if task
       (-> db
           (assoc-in [:edit] task)
           (assoc-in [:page] :edit))
       (do
         (routes/nav! "/")
         db)))))

(re-frame/reg-event-db
 :filter-update
 (fn [db [_ filter]]
   (assoc-in db [:filter] filter)))

(re-frame/reg-event-db
 :tasks-update
 (fn [db [_ tasks]]
   (assoc-in db [:tasks] tasks)))

(re-frame/reg-event-db
 :toggle-details
 (fn [db [_ show-details]]
   (assoc-in db [:show-details] show-details)))
