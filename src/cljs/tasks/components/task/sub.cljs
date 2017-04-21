(ns tasks.components.task.sub
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :tasks
 (fn [db _] (:tasks db)))
