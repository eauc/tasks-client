(ns tasks.routes
  (:import goog.History)
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]))

(defroute home "/" {}
  (re-frame/dispatch [:page :home]))

(defroute edit "/edit/:id" [id]
  (re-frame/dispatch [:edit-start id]))

(defonce history (History.))

(defn nav!
  ([page] (.setToken history (page)))
  ([page params] (.setToken history (page params))))

(defn hook-browser-navigation! []
  (events/listen history EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (.setEnabled history true))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  (hook-browser-navigation!))
