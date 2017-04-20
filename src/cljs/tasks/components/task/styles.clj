(ns tasks.components.task.styles)

(def styles
  [:&-task {:user-select "none"}
   [:&-header {:display "flex"
               :flex-flow "row nowrap"
               :align-items "center"}]
   [:&.done [:.tasks-task-title {:opacity 0.6
                                 :font-style "italic"
                                 :text-decoration "line-through"}]]
   [:&.show-details
    [:.tasks-task-action {:display "block"}]
    [:.tasks-task-body {:display "block"}]]
   [:&-action {:display "none"
               :background-color "white"
               :border "1px solid rgba(0,0,0,0.5)"
               :border-radius "3px"
               :margin "0.5em"
               :padding "0.5em 0.8em"}]
   [:&-body {:display "none"}]
   [:&-done [:input {:margin "0.75em"}]]
   [:&-title {:flex-grow 1}]

   [:&-edit {:display "flex"
             :flex-flow "column nowrap"}
    [:&-body {:min-height "8em"}]]])
