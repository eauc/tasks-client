(ns tasks.styles
  (:require [garden.def :refer [defstyles]]
            [tasks.styles.form :as form]
            [tasks.components.nav.styles :as nav]
            [tasks.components.task.styles :as task]))

(defstyles screen
  [:* {:font-family "Droid Sans"
       :color "rgba(0,0,0,0.85)"}]
  [:body {:padding 0
          :margin 0}]
  form/styles
  [:.tasks
   {:align-items "center"
    :display "flex"
    :flex-flow "column nowrap"
    :height "100vh"}
   nav/styles
   [:&-body {:box-shadow "0 15px 15px rgba(0,0,0,0.3)"
             :display "flex"
             :flex-flow "column nowrap"
             :flex-grow "1"
             :max-width "800px"
             :position "relative"
             :width "100%"}]
   task/create
   task/edit
   task/tasks-list
   task/view])
