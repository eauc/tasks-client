(ns tasks.components.page.handler
  (:require [re-frame.core :as re-frame]
            [tasks.debug :refer [debug?]]))

(re-frame/reg-event-db
 :page
 [(when debug? re-frame/debug)]
 (fn [db [_ page]] (assoc-in db [:page] page)))
