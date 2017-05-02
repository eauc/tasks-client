(ns tasks.components.prompt.styles
  (:require [tasks.styles.colors :refer [colors]]))

(def styles
  [:&-prompt {:align-items "center"
              :background-color "rgba(0,0,0,0.3)"
              :bottom 0
              :display "flex"
              :flex-flow "column nowrap"
              :justify-content "space-around"
              :left 0
              :position "absolute"
              :right 0
              :top 0
              :z-index 2000}
   [:&.hidden {:opacity 0
               :pointer-events "none"}]
   [:&-form {:background-color "white"
             :border "1px solid rgba(0,0,0,0.3)"
             :border-radius "3px"
             :box-shadow "2px 2px 3px 1px rgba(0,0,0,0.2)"
             :display "flex"
             :flex-flow "column nowrap"
             :margin "8px"
             :padding "0.5em"}]
   [:&-message {:margin "0.5em 0"
                :text-align "center"}]
   [:&-input {:margin "0.5em 0"}
    [:.tasks-input-error {:display "none"}]]
   [:&-actions {:display "flex"
                :flex-flow "row nowrap"
                :justify-content "center"
                :margin "0.5em 0"}]
   [:&-ok {:background-color (:accent-color colors)
           :border-color "rgba(0,0,0,0.3)"
           :color (:text-primary-color colors)
           :margin "0 0.5em"
           :width "5em"}]
   [:&-cancel {:margin "0 0.5em"
               :width "5em"}]])
