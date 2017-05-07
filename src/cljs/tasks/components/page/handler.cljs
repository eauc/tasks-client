(ns tasks.components.page.handler
  (:require [re-frame.core :as re-frame]
            [tasks.db :as db]))

(re-frame/reg-event-db
 :page
 db/default-interceptors
 (fn page-event [db [_ page]]
   (assoc-in db [:page] page)))
