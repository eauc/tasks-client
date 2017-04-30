(ns tasks.components.list.edit
  (:require [cljs.spec :as spec]
            [re-frame.core :as re-frame]
            [tasks.components.form.input :as form-input]
            [tasks.components.list.handler]
            [tasks.components.list.sub]
            [tasks.routes :as routes]
            [tasks.utils :as utils]
            [tasks.debug :as debug]))

(defn render [edit {:keys [on-cancel on-save on-update]}]
  (let [on-save-debounce (utils/debounce on-save 250)
        on-submit (fn [event]
                    (-> event .preventDefault)
                    (on-save-debounce))]
    (fn [_ {:keys [errors]}]
      [:form {:class (if (not (empty? errors)) "error")
              :on-submit on-submit}
       [:fieldset
        [:legend "Edit Tasks List"]
        [:div.tasks-list-edit
         [form-input/render :input
          {:auto-focus true
           :error (:new-name errors)
           :field [:new-name]
           :label "Name"
           :on-update on-update
           :type "text"
           :value (:new-name edit)}]]
        [:div
         [:button {:type "submit"} "Save"]
         [:button {:type "button" :on-click on-cancel} "Cancel"]]]])))

(defn component [{:keys [on-save-event]}]
  (let [edit (re-frame/subscribe [:list-edit])
        errors (re-frame/subscribe [:list-edit-errors])
        on-cancel #(re-frame/dispatch [:nav routes/home])
        on-save #(re-frame/dispatch [on-save-event])
        on-update #(re-frame/dispatch [:list-edit-update %1 %2])]
    (fn []
      [render @edit {:errors @errors
                     :on-cancel on-cancel
                     :on-save on-save
                     :on-update on-update}])))
