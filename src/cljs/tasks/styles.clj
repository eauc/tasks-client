(ns tasks.styles
  (:require [garden.def :refer [defstyles]]
            [tasks.styles.form :as form]
            [tasks.components.task.styles :as task]))

(defstyles screen
  [:* {:font-family "Droid Sans"
       :color "rgba(0,0,0,0.85)"}]
  form/styles
  [:.tasks
   task/edit
   task/list
   task/view])
