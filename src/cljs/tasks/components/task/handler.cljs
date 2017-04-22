(ns tasks.components.task.handler
  (:require [re-frame.core :as re-frame]
            [tasks.db :as db]
            [tasks.debug :refer [debug?]]
            [tasks.models.tasks :as tasks]
            [tasks.routes :as routes]
            ;; [clairvoyant.core :refer-macros [trace-forms]]
            ;; [re-frame-tracer.core :refer [tracer]]
            ))

;; (trace-forms {:tracer (tracer :color "green")}

(def interceptors
  [(when debug? re-frame/debug)])

(re-frame/reg-event-db
 :create-save
 interceptors
 (fn create-save [db [_ task]]
   [db [_ task]]
   (-> db
       (update-in [:tasks] #(cons task %))
       (assoc-in [:edit] nil))))

(re-frame/reg-event-db
 :create-start
 interceptors
 (fn create-start [db _]
   (-> db
       (assoc-in [:edit] {:id (str (.now js/Date))
                          :title ""
                          :body ""
                          :done false})
       (assoc-in [:page] :create))))

(re-frame/reg-event-db
 :edit-update
 interceptors
 (fn edit-update [db [_ edit]]
   (assoc-in db [:edit] edit)))

(re-frame/reg-event-db
 :edit-save
 interceptors
 (fn edit-save [db [_ task]]
   (-> db
       (update-in [:tasks] tasks/update-task task)
       (assoc-in [:edit] nil))))

(re-frame/reg-event-db
 :edit-start
 interceptors
 (fn edit-start [db [_ id]]
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
 interceptors
 (fn filter-update [db [_ filter]]
   (assoc-in db [:filter] filter)))

(re-frame/reg-event-db
 :tasks-update
 interceptors
 (fn tasks-update [db [_ tasks]]
   (assoc-in db [:tasks] tasks)))

(re-frame/reg-event-db
 :toggle-details
 interceptors
 (fn toggle-details [db [_ show-details]]
   (assoc-in db [:show-details] show-details)))

;; )
