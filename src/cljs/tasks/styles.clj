(ns tasks.styles
  (:require [garden.def :refer [defstyles]]
            [tasks.components.task.styles :as task]))

(defstyles screen
  [:* {:font-family "Droid Sans"
       :color "rgba(0,0,0,0.85)"}]
  [:.tasks
   task/styles])
