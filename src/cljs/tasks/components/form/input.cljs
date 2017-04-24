(ns tasks.components.form.input
  (:require [reagent.core :as reagent]))

(defn render [input {:keys [class on-update value]}]
  (let [current-value (reagent/atom value)
        on-change (fn [event]
                    (reset! current-value (-> event .-target .-value))
                    (on-update @current-value))]
    (fn [_ {:keys [has-error] :as props}]
      [input (merge props {:class (str class (if has-error " error"))
                           :has-error nil
                           :on-change on-change
                           :on-update nil
                           :value @current-value})])))
