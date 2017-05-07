(ns tasks.components.form.input
  (:require [reagent.core :as reagent]
            [tasks.utils :as utils]))

(defn render [input {:keys [class field label on-update value] :as base-props}]
  (let [current-value (reagent/atom value)
        on-update-debounce (utils/debounce on-update 250)
        on-change (fn [event]
                    (reset! current-value (-> event .-target .-value))
                    (on-update-debounce field @current-value))
        props (-> base-props
                  (dissoc :field :error :label :on-update :spec)
                  (assoc :placeholder label)
                  (assoc :on-change on-change))]
    (fn input-render [_ {:keys [error]}]
      [:div.tasks-input {:class (str class (if error "error"))}
       (when label [:label label])
       [input (merge props {:value @current-value})]
       [:p.tasks-input-error
        (or error "No error")]])))
