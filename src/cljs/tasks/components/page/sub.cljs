(ns tasks.components.page.sub
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :page
 (fn [db _] (:page db)))
