(ns tasks.components.list.edit
  (:require [cljs.spec :as spec]
            [re-frame.core :as re-frame]
            [tasks.components.form.input :as form-input]
            [tasks.components.list.handler]
            [tasks.components.list.sub]
            [tasks.routes :as routes]
            [tasks.utils :as utils]))

(defn render [edit {:keys [on-cancel on-save on-update]}]
  [:form {:on-submit (fn [event]
                       (-> event .preventDefault)
                       (on-save edit))}
   [:fieldset
    [:legend "Edit Tasks List"]
    [:div.tasks-list-edit
     [form-input/render :input
      {:auto-focus true
       :on-update #(on-update (assoc edit :new-name %))
       :placeholder "Name"
       :type "text"
       :value (:new-name edit)}]]
    [:div
     [:button {:type "submit"} "Save"]
     [:button {:type "button"
               :on-click #(on-cancel edit)} "Cancel"]]]])

(defn component [{:keys [on-save-event]}]
  (let [edit (re-frame/subscribe [:list-edit])
        on-cancel #(re-frame/dispatch [:nav routes/home])
        on-save #(re-frame/dispatch [on-save-event %])
        on-update (utils/debounce (fn [edit] (re-frame/dispatch [:list-edit-update edit])) 250)]
    (fn []
      [render @edit {:on-cancel on-cancel
                     :on-save on-save
                     :on-update on-update}])))
