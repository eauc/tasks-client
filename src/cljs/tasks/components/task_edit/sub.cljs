(ns tasks.components.task-edit.sub
  (:require [re-frame.core :as re-frame]
            [tasks.models.task-edit :as task-edit]))

(re-frame/reg-sub
 :task-edit
 (fn task-edit [db _]
   (:task-edit db)))

(re-frame/reg-sub
 :task-edit-errors
 :<- [:task-edit]
 (fn task-edit-errors [edit _]
   (task-edit/describe-errors edit)))
