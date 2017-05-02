(ns tasks.components.prompt.view-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.debug :as debug]
            [tasks.components.form.input :as input]
            [tasks.components.prompt.view :as prompt]))

(defcard-rg prompt-hide
  "### Prompt component, hidden"

  (fn [state-atom _]
    [:div {:style {:background-color "white"
                   :height "300px"
                   :position "relative"
                   :width "600px"}}
     [:p "Content"]
     [prompt/render (:prompt @state-atom)]])

  (reagent/atom {:prompt nil})

  {:inspect-data true})

(defcard-rg prompt-alert
  "### Prompt component, alert"

  (fn [state-atom _]
    [:div {:style {:background-color "white"
                   :height "300px"
                   :position "relative"
                   :width "600px"}}
     [:p "Content"]
     [prompt/render (:prompt @state-atom)
      {:on-cancel #(debug/spy "Cancel!")
       :on-validate #(debug/spy "Save!")}]])

  (reagent/atom {:prompt {:type :alert
                          :message "This is an alert !"}})

  {:inspect-data true})

(defcard-rg prompt-comfirm
  "### Prompt component, confirm"

  (fn [state-atom _]
    [:div {:style {:background-color "white"
                   :height "300px"
                   :position "relative"
                   :width "600px"}}
     [:p "Content"]
     [prompt/render (:prompt @state-atom)
      {:on-cancel #(debug/spy "Cancel!")
       :on-validate #(debug/spy "Save!")}]])

  (reagent/atom {:prompt {:type :confirm
                          :message "Please confirm what you want:"}})

  {:inspect-data true})

(defcard-rg prompt-string
  "### Prompt component, prompt [string]"

  (fn [state-atom _]
    [:div {:style {:background-color "white"
                   :height "300px"
                   :position "relative"
                   :width "600px"}}
     [:p "Content"]
     [prompt/render (:prompt @state-atom)
      {:on-cancel #(debug/spy "Cancel!")
       :on-update #(swap! state-atom assoc-in [:prompt :value] %)
       :on-validate #(debug/spy "Save!")}]])

  (reagent/atom {:prompt {:type :prompt
                          :value "toto"
                          :message "Type the desired value:"}})

  {:inspect-data true})

(defcard-rg prompt-number
  "### Prompt component, prompt [number]"

  (fn [state-atom _]
    [:div {:style {:background-color "white"
                   :height "300px"
                   :position "relative"
                   :width "600px"}}
     [:p "Content"]
     [prompt/render (:prompt @state-atom)
      {:on-cancel #(debug/spy "Cancel!")
       :on-update #(swap! state-atom assoc-in [:prompt :value] %)
       :on-validate #(debug/spy "Save!")}]])

  (reagent/atom {:prompt {:type :prompt
                          :value 42
                          :message "Type the desired value:"}})

  {:inspect-data true})
