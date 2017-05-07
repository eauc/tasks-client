(ns tasks.components.tasks-list.view
  (:require [re-frame.core :as re-frame]
            [tasks.components.form.input :as form-input]
            [tasks.components.task.view :as task-view]
            [tasks.components.tasks-list.create :as tasks-list-create]
            [tasks.components.tasks-list.handler]
            [tasks.components.tasks-list.sub]
            [tasks.models.task :as task]
            [tasks.routes :as routes]))

(defn toggle-details-id [current new]
  (if (= current new) nil new))

(defn render-filter-form [tasks {:keys [filter on-filter on-update]}]
  [:form.tasks-list-filter
   {:on-submit #(-> % .preventDefault)}
   [form-input/render :input
    {:field [:filter]
     :on-update on-filter
     :placeholder "Filter"
     :type "text"
     :value filter}]
   [:button.tasks-list-check-all
    {:dangerouslySetInnerHTML {:__html "&#x2713;"}
     :on-click #(on-update (task/toggle-all-done tasks))}]])

(defn render [tasks {:keys [filter on-update show-details toggle-details] :as props}]
  (let [on-task-delete #(on-update (task/delete-task tasks %))
        on-task-update #(on-update (task/update-task tasks %))]
    [:div.tasks-list
     [tasks-list-create/component]
     [render-filter-form tasks props]
     [:div.tasks-list-body
      (for [task tasks]
        ^{:key (:id task)}
        [task-view/render task
         (merge props {:on-delete on-task-delete
                       :on-update on-task-update
                       :show-details (= show-details (:id task))
                       :toggle-details #(toggle-details (toggle-details-id show-details (:id task)))})])]]))

(defn component []
  (let [filter (re-frame/subscribe [:tasks-filter])
        on-edit #(re-frame/dispatch [:nav routes/task-edit {:id (:id %)}])
        on-filter #(re-frame/dispatch [:tasks-filter-update %2])
        on-update #(re-frame/dispatch [:tasks-update %])
        show-details (re-frame/subscribe [:tasks-show-details])
        tasks (re-frame/subscribe [:tasks-sorted])
        toggle-details #(re-frame/dispatch [:tasks-toggle-details %])]
    (fn tasks-list-component []
      [render @tasks
       {:filter @filter
        :on-edit on-edit
        :on-filter on-filter
        :on-update on-update
        :show-details @show-details
        :toggle-details toggle-details}])))
