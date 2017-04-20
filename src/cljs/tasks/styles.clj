(ns tasks.styles
	(:require [garden.def :refer [defstyles]]
						[tasks.components.task.styles :as task]))

(def input-styles
	{:margin "1em"
	 :padding "0.8em"
	 :border "1px solid rgba(0,0,0,0.3)"
	 :border-radius "3px"})

(defstyles screen
	[:* {:font-family "Droid Sans"
			 :color "rgba(0,0,0,0.85)"}]
	[:fieldset {:border "1px solid rgba(0,0,0,0.3)"}]
	[:legend {:font-style "italic"
						:opacity 0.8}]
	[:input input-styles]
	[:textarea input-styles]
  [:button {:background-color "white"
            :border "1px solid rgba(0,0,0,0.5)"
            :border-radius "3px"
            :cursor "pointer"
            :margin "0.5em"
            :padding "0.5em 0.8em"}]
	[:.tasks
	 task/styles])
