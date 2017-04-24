(ns tasks.components.task.handler
  (:require [re-frame.core :as re-frame]
            [tasks.db :as db]
            [tasks.debug :refer [debug?]]
            [tasks.models.tasks :as tasks]
            [tasks.routes :as routes]
            ;; [clairvoyant.core :refer-macros [trace-forms]]
            ;; [re-frame-tracer.core :refer [tracer]]

            [tasks.debug :as debug]))

;; (trace-forms {:tracer (tracer :color "green")}

(re-frame/reg-cofx
 :task-id
 (fn [coeffects _]
   (assoc coeffects :task-id (str (.now js/Date)))))

(def interceptors
  [db/check-spec-interceptor
   (when debug? re-frame/debug)])

(re-frame/reg-event-fx
 :create-save
 interceptors
 (fn create-save [{:keys [db]} [_ task]]
   {:db (-> db
            (update-in [:tasks] #(cons task %))
            (assoc-in [:edit] nil))
    :nav {:route routes/home}}))

(re-frame/reg-event-fx
 :create-start
 [(re-frame/inject-cofx :task-id)
  interceptors]
  (fn create-start [{:keys [db task-id]} _]
    {:db (-> db
             (assoc-in [:edit] {:id task-id
                                :title ""
                                :body ""
                                :done false})
             (assoc-in [:page] :create))}))

(re-frame/reg-event-fx
  :current-list
  [(re-frame/inject-cofx :storage-list)
   interceptors]
  (fn current-list [{:keys [db storage]} [_ list]]
    {:db (merge db storage)}))

(re-frame/reg-event-db
 :edit-update
 [interceptors
  (re-frame/path :edit)]
 (fn edit-update [db [_ edit]]
   edit))

(re-frame/reg-event-fx
 :edit-save
 interceptors
 (fn edit-save [{:keys [db]} [_ task]]
   {:db (-> db
            (update-in [:tasks] tasks/update-task task)
            (assoc-in [:edit] nil))
    :nav {:route routes/home}}))

(re-frame/reg-event-fx
 :edit-start
 interceptors
 (fn edit-start [{:keys [db]} [_ id]]
   (let [tasks (:tasks db)
         task (first (filter #(= (:id %) id) tasks))]
     (if task
       {:db (-> db
                (assoc-in [:edit] task)
                (assoc-in [:page] :edit))
        :nav {:route routes/edit :params {:id (:id task)}}}
       {:nav {:route routes/home}}))))

(re-frame/reg-event-db
 :filter-update
 [interceptors
  (re-frame/path :filter)]
 (fn filter-update [db [_ filter]]
   filter))

(re-frame/reg-event-db
 :tasks-update
 [interceptors
  (re-frame/path :tasks)]
 (fn tasks-update [db [_ tasks]]
   tasks))

(re-frame/reg-event-db
 :toggle-details
 [interceptors
  (re-frame/path :show-details)]
 (fn toggle-details [db [_ show-details]]
   show-details))

;; )
