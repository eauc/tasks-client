(ns tasks.components.tasks-list.styles
  (:require [tasks.styles.colors :refer [borders colors]]))

(def tasks-list-styles
  [:&-list {:display "flex"
            :flex-flow "column nowrap"}
   [:&-filter {:align-items "center"
               :display "flex"
               :flex-flow "row nowrap"
               :flex-shrink 0
               :border-bottom (:default-light borders)}
    [:.tasks-input {:flex-grow 1}]
    [:.tasks-input-error {:display "none"}]]
   [:&-check-all {:border 0
                  :font-weight "bold"
                  :font-size "1.4em"
                  :height "2em"
                  :padding 0
                  :margin 0
                  :width "2em"}]
   [:&-body {:flex-shrink 1
             :overflow-x "hidden"
             :overflow-y "auto"}]
   [:.tasks-view {:border-bottom (:default-light borders)}]])

(def tasks-create-styles
  [:&-create {:background-color (:accent-color colors)
              :border 0
              :border-radius "0.75em"
              :box-shadow "0 2px 3px 0 rgba(0,0,0,0.5)"
              :bottom "0.5em"
              :color (:text-primary-color colors)
              :cursor "pointer"
              :height "1.5em"
              :font-size "2em"
              :position "absolute"
              :right "0.5em"
              :width "1.5em"}
   [:&:hover {:background-color (:dark-accent-color colors)}]
   [:&:active {:background-color (:darker-accent-color colors)}]])
