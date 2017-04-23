(ns tasks.components.task.edit
  (:require [cljs.spec :as spec]
            [re-frame.core :as re-frame]
            [tasks.routes :as routes]
            [reagent.core :as reagent]))

(defn debounce [fun ms]
  (let [timeout (atom nil)]
    (fn [& args]
      (let [caller (fn []
                     (.log js/console "debounce caller")
                     (reset! timeout nil)
                     (apply fun args))]
        (when @timeout
          (.clearTimeout js/window @timeout))
        (reset! timeout
                (.setTimeout js/window caller ms))))))

(defn problem-for-field [problems field]
  (filter #(= field (:path %)) problems))

(defn field-has-problem [problems field]
  (not (empty? (problem-for-field problems field))))

(defn render-title-input [value {:keys [on-update placeholder]}]
  (let [current-value (reagent/atom value)
        on-change (fn [event]
                    (reset! current-value (-> event .-target .-value))
                    (on-update @current-value))]
    (fn [_ {:keys [error]}]
      [:input
       {:type "text"
        :placeholder placeholder
        :on-change on-change
        :value @current-value
        :class (if error "error")}])))

(defn render-body-input [value {:keys [on-update placeholder]}]
  (let [current-value (reagent/atom value)
        on-change (fn [event]
                    (reset! current-value (-> event .-target .-value))
                    (on-update @current-value))]
    (fn [_ {:keys [error]}]
      [:textarea.tasks-edit-body
       {:placeholder placeholder
        :on-change on-change
        :value @current-value
        :class (if error "error")}])))

(defn render [task {:keys [on-cancel on-update on-save]}]
  (let [valid (spec/valid? :tasks.models.task/task task)
        problems (:cljs.spec/problems (spec/explain-data :tasks.models.task/task task))]
    ;; (.log js/console "render")
    [:form {:class (if (not valid) "error")
            :on-submit (fn [event]
                         (.preventDefault event)
                         (on-save task))}
     [:fieldset
      [:legend "Edit Task"]
      [:div.tasks-edit
       [render-title-input (:title task)
        {:error (field-has-problem problems [:title])
         :on-update #(on-update (assoc-in task [:title] %))
         :placeholder "Title"}]
       [render-body-input (:body task)
        {:error (field-has-problem problems [:body])
         :on-update #(on-update (assoc-in task [:body] %))
         :placeholder "Body"}]
       [:div
        [:button {:type "submit"} "Save"]
        [:button {:type "button"
                  :on-click #(on-cancel task)} "Cancel"]]]]]))

(defn component [{:keys [on-save-event]}]
  (let [edit (re-frame/subscribe [:edit])
        on-cancel #(re-frame/dispatch [:nav routes/home])
        on-save #(re-frame/dispatch [on-save-event %])
        on-update (debounce (fn [task] (re-frame/dispatch [:edit-update task])) 250)]
    (fn [_]
      [render @edit
       {:on-cancel on-cancel
        :on-save on-save
        :on-update on-update}])))
