(ns tasks.components.prompt.view
  (:require [re-frame.core :as re-frame]
            [tasks.components.form.input :as input]
            [tasks.components.prompt.handler]
            [tasks.components.prompt.sub]))

(defn render [_ {:keys [on-cancel on-update on-validate]}]
  (let [on-form-click #(-> % .stopPropagation)
        on-form-submit (fn [event]
                         (-> event .preventDefault)
                         (-> event .stopPropagation)
                         (on-validate))
        on-value-update #(on-update %2)]
    (fn prompt-render [{:keys [message type value] :as prompt} _]
      [:div.tasks-prompt {:class (if (nil? prompt) "hidden")
                          :on-click on-cancel}
       [:form.tasks-prompt-form
        {:on-click on-form-click
         :on-submit on-form-submit}
        [:p.tasks-prompt-message message]
        (when (= type :prompt)
          [input/render :input
           {:auto-focus true
            :class "tasks-prompt-input"
            :field [:value]
            :on-update on-value-update
            :type (if (number? value) "number" "text")
            :value value}])
        [:div.tasks-prompt-actions
         [:button.tasks-prompt-ok
          {:on-click on-form-submit
           :type "submit"}
          "Ok"]
         (when-not (= type :alert)
           [:button.tasks-prompt-cancel
            {:on-click on-cancel
             :type "button"}
            "Cancel"])]]])))

(defn component []
  (let [on-cancel #(re-frame/dispatch [:prompt-cancel])
        on-update #(re-frame/dispatch [:prompt-update %])
        on-validate #(re-frame/dispatch [:prompt-validate])
        prompt (re-frame/subscribe [:prompt])]
    (fn prompt-component []
      [render @prompt
       {:on-cancel on-cancel
        :on-update on-update
        :on-validate on-validate}])))
