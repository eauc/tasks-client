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
   [:&-menu {:cursor "pointer"
             :padding "0.5em"
             :position "relative"}
    [:&:active {:background-color (:dark-primary-color colors)}]
    [:&.hide [:.tasks-nav-lists {:display "none"}]]]
   [:&-lists {:background-color "#FFFFFF"
              :border "1px solid rgba(0,0,0,0.3)"
              :border-radius "3px"
              :left "0"
              :position "absolute"
              :top "100%"
              :z-index 1000}]
   [:&-list {:color (:default-text colors)
             :padding "0.5em 1em"}
    [:&:hover {:background-color "rgba(0,0,0,0.15)"}]
    [:&:active {:background-color "rgba(0,0,0,0.3)"}]]
   [:&-header {:flex-grow 1
               :padding "0.5em"}]])
