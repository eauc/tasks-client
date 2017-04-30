(ns tasks.components.list.sub
  (:require [re-frame.core :as re-frame]
            [tasks.models.list :as list-model]))

(re-frame/reg-sub
 :lists
 (fn tasks [db _] (:lists db)))

(re-frame/reg-sub
 :list-edit
 (fn list-edit [db _] (:list-edit db)))

(re-frame/reg-sub
 :list-edit-errors
 :<- [:list-edit]
 :<- [:lists]
 (fn [[edit lists] _]
   (list-model/describe-errors edit lists)))
