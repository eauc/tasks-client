(ns tasks.styles.form
  (:require [garden.def :refer [defstyles]]
            [garden.selectors :refer [attr= defselector]]))

(defselector button)

(defstyles styles
  [:form
   [:fieldset {:border "1px solid rgba(0,0,0,0.3)"}]
   [:legend {:font-style "italic"
             :opacity 0.8}]
   [:input :textarea
    {:margin "1em"
     :padding "0.8em"
     :border "1px solid rgba(0,0,0,0.3)"
     :border-radius "3px"}
    [:&.error {:border-color "red"}
     [:&:focus { :outline-color "red"}]]]
   [:button {:background-color "white"
             :border "1px solid rgba(0,0,0,0.5)"
             :border-radius "3px"
             :cursor "pointer"
             :margin "0.5em"
             :padding "0.5em 0.8em"}]
   [:&.error
    [(button (attr= :type "submit"))
     {:opacity 0.3
      :pointer-events "none"}]]])
