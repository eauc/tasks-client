(ns tasks.components.task.edit
  (:require [cljs.spec :as spec]
            [re-frame.core :as re-frame]
            [tasks.components.form.input :as form-input]
            [tasks.routes :as routes]
            [tasks.utils :as utils]
            [tasks.debug :as debug]))

(defn render [task {:keys [errors on-cancel on-update on-save]}]
  (let [on-save-debounce (utils/debounce on-save 250)
        on-submit (fn [event]
                    (.preventDefault event)
                    (on-save-debounce))]
    [:form {:class (if (not (empty? errors)) "error")
            :on-submit on-submit}
     [:fieldset
      [:legend "Edit Task"]
      [:div.tasks-edit
       [form-input/render :input
        {:autoFocus true
         :error (:title errors)
         :field [:title]
         :label "Title"
         :on-update on-update
         :type "text"
         :value (:title task)}]
       [form-input/render :textarea
        {:class "tasks-edit-body"
         :error (:body errors)
         :field [:body]
         :label "Body"
         :on-update on-update
         :value (:body task)}]
       [:div
        [:button {:type "submit"} "Save"]
        [:button {:type "button" :on-click on-cancel} "Cancel"]]]]]))

(defn component [{:keys [on-save-event]}]
  (let [edit (re-frame/subscribe [:edit])
        errors (re-frame/subscribe [:edit-errors])
        on-cancel #(re-frame/dispatch [:nav routes/home])
        on-save #(re-frame/dispatch [on-save-event])
        on-update #(re-frame/dispatch [:edit-update %1 %2])]
    (fn [_]
      [render @edit
       {:errors @errors
        :on-cancel on-cancel
        :on-save on-save
        :on-update on-update}])))
