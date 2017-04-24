(ns tasks.components.task.sub
  (:require [re-frame.core :as re-frame]
            [tasks.models.tasks :as tasks]
            ;; [clairvoyant.core :refer-macros [trace-forms]]
            ;; [re-frame-tracer.core :refer [tracer]]
            ))

;; (trace-forms {:tracer (tracer :color "brown")}

(re-frame/reg-sub
 :current-list
 (fn edit [db _] (:current-list db)))

(re-frame/reg-sub
  :edit
  (fn edit [db _] (:edit db)))

(re-frame/reg-sub
 :filter
 (fn filter [db _] (:filter db)))

(re-frame/reg-sub
 :lists
 (fn tasks [db _] (:lists db)))

(re-frame/reg-sub
  :show-details
  (fn show-details [db _] (:show-details db)))

(re-frame/reg-sub
 :tasks
 (fn tasks [db _] (:tasks db)))

(re-frame/reg-sub
 :tasks-filtered
 :<- [:tasks]
 :<- [:filter]
 (fn filtered [[tasks filter] _] (tasks/filter-with tasks filter)))

(re-frame/reg-sub
 :tasks-sorted
 :<- [:tasks-filtered]
 (fn tasks-sorted [tasks _] (tasks/sort-by-title tasks)))

;; )
