(ns tasks.components.nav.styles
  (:require [tasks.styles.colors :refer [colors]]))

(def styles
  [:&-nav {:align-items "stretch"
           :background-color (:default-primary-color colors)
           :display "flex"
           :flex-flow "row nowrap"
           :flex-shrink 0
           :font-size "1.5em"
           :width "100vw"}
   [:* {:color (:text-primary-color colors)}]
   [:&-menu {:padding "0.5em"
             :cursor "pointer"}
    [:&:active {:background-color (:dark-primary-color colors)}]]
   [:&-header {:flex-grow 1
               :padding "0.5em"}]])
