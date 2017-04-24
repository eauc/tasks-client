(ns tasks.components.form.input
  (:require [reagent.core :as reagent]))

(defn render [input {:keys [class on-update value]}]
  (let [current-value (reagent/atom value)
        on-change (fn [event]
                    (reset! current-value (-> event .-target .-value))
                    (on-update @current-value))]
    (fn [_ {:keys [has-error] :as props}]
      [input (-> props
                 (merge {:class (str class (if has-error " error"))
                         :on-change on-change
                         :value @current-value})
                 (dissoc :has-error :on-update))])))
