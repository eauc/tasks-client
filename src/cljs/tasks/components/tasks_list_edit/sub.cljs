(ns tasks.components.tasks-list-edit.sub
  (:require [tasks.models.tasks-list-edit :as tasks-list-edit]
            [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :tasks-list-edit
 (fn tasks-list-edit [db _]
   (:tasks-list-edit db)))

(re-frame/reg-sub
 :tasks-list-edit-errors
 :<- [:tasks-list-edit]
 :<- [:tasks-lists-names]
 (fn tasks-list-edit-errors [[edit lists-names] _]
   (tasks-list-edit/describe-errors edit lists-names)))
