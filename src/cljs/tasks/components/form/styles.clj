(ns tasks.components.form.styles
  (:require [garden.selectors :refer [attr= defselector]]
            [tasks.styles.colors :refer [borders colors]]))

(defselector button)

(def form-styles
  [:form
   [:fieldset {:border (:default-light borders)}]
   [:legend {:font-style "italic"
             :opacity 0.8}]
   [:input :textarea
    {:margin "0"
     :padding "0.8em"
     :border (:default-light borders)
     :border-radius "3px"}]
   [:button {:background-color "white"
             :border (:default borders)
             :border-radius "3px"
             :cursor "pointer"
             :margin "0.5em"
             :padding "0.5em 0.8em"}]
   [(button (attr= :type "submit"))
    {:background-color (:accent-color colors)
     :border (:default-light borders)
     :color (:text-primary-color colors)}]
   [:&.error
    [(button (attr= :type "submit"))
     {:opacity 0.3
      :pointer-events "none"}]]])

(def input-styles
  [:&-input {:display "flex"
             :flex-flow "column nowrap"
             :margin "0.5em 0"
             :padding "0 1em"}
   [:label {:margin-bottom "0.25em"}]
   [:&-error {:font-size "0.8em"
              :font-style "italic"
              :font-weight "bold"
              :color "transparent"
              :margin "0.25em 0 0 0"}]
   [:&.error
    [:input {:border-color "red"}
     [:&:focus { :outline-color "red"}]]
    [:.tasks-input-error {:color "rgba(255,0,0,0.85)"}]]])
