(ns tasks.components.list.sub
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  :list-edit
  (fn list-edit [db _] (:list-edit db)))
