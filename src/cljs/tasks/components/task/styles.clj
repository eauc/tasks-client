(ns tasks.components.task.styles
  (:require [tasks.styles.colors :refer [colors]]))

(def create
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

(def edit
  [:&-edit {:display "flex"
            :flex-flow "column nowrap"}
   [:&-body {:min-height "8em"}]])

(def tasks-list
  [:&-list {:display "flex"
            :flex-flow "column nowrap"}
   [:&-filter {:display "flex"
               :flex-flow "column nowrap"
               :flex-shrink 0
               :border-bottom "1px solid rgba(0,0,0,0.2)"}]
   [:&-body {:flex-shrink 1
             :overflow-x "hidden"
             :overflow-y "auto"}]
   [:.tasks-view {:border-bottom "1px solid rgba(0,0,0,0.2)"}]])

(def view
  [:&-view {:user-select "none"}
   [:&-header {:align-items "center"
               :display "flex"
               :flex-flow "row nowrap"}]
   [:&.done [:.tasks-view-title {:opacity 0.6
                                 :font-style "italic"
                                 :text-decoration "line-through"}]]
   [:&.show-details
    [:.tasks-view-action {:display "block"}]]
   [:&.show-body
    [:.tasks-view-body {:display "block"}]]
   [:&-action {:display "none"
               :background-color "white"
               :border "1px solid rgba(0,0,0,0.5)"
               :border-radius "3px"
               :cursor "pointer"
               :margin "0 0.5em"
               :padding "0.5em 0.8em"}]
   [:&-body {:display "none"
             :padding "0 1em"}]
   [:&-done [:input {:margin "0.75em"}]]
   [:&-title {:cursor "pointer"
              :flex-grow 1
              :font-weight 500
              :overflow-x "hidden"
              :margin "0.5em 0"
              :text-overflow "ellipsis"}]])
