(ns tasks.components.task.styles
  (:require [tasks.styles.colors :refer [borders]]))

(def task-styles
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
               :border (:default borders)
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
