(ns tasks.components.form.input
  (:require [reagent.core :as reagent]
            [tasks.utils :as utils]
            [cljs.spec :as spec]))

(defn render [input {:keys [class field on-update spec value] :as base-props}]
  (let [current-value (reagent/atom value)
        on-update-debounce (utils/debounce on-update 250)
        on-change (fn [event]
                    (reset! current-value (-> event .-target .-value))
                    (on-update-debounce field @current-value))
        props (-> base-props
                  (dissoc :field :has-error :on-update :spec)
                  (assoc :on-change on-change))]
    (fn [_ _]
      (let [has-error (not (spec/valid? spec @current-value))]
        [input (merge props {:class (str class (if has-error " error"))
                             :value @current-value})]))))
