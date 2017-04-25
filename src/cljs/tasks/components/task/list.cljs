(ns tasks.components.task.list
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [tasks.components.form.input :as form-input]
            [tasks.components.task.create :as task-create]
            [tasks.components.task.handler]
            [tasks.components.task.sub]
            [tasks.components.task.view :as task-view]
            [tasks.debug :as debug]
            [tasks.models.tasks :as tasks]
            [tasks.routes :as routes]
            [tasks.utils :as utils]
            ;; [clairvoyant.core :refer-macros [trace-forms]]
            ;; [re-frame-tracer.core :refer [tracer]]
            ))

;; (trace-forms {:tracer (tracer :color "orange")}

(defn toggle-details-id [current new]
  (if (= current new) nil new))

(defn render-filter-form [tasks {:keys [filter on-filter on-update]}]
  [:form.tasks-list-filter
   {:on-submit #(-> % .preventDefault)}
   [form-input/render :input
    {:on-update on-filter
     :placeholder "Filter"
     :type "text"
     :value filter}]
   [:button.tasks-list-check-all
    {:dangerouslySetInnerHTML {:__html "&#x2713;"}
     :on-click #(on-update (tasks/toggle-done tasks))}]])

(defn render [tasks {:keys [filter on-update show-details toggle-details] :as props}]
  [:div.tasks-list
   [task-create/component]
   [render-filter-form tasks props]
   [:div.tasks-list-body
    (for [task tasks]
      ^{:key (:id task)}
      [task-view/render task
       (merge props {:on-delete #(on-update (tasks/delete-task tasks %))
                     :on-update #(on-update (tasks/update-task tasks %))
                     :show-details (= show-details (:id task))
                     :toggle-details #(toggle-details (toggle-details-id show-details (:id task)))})])]])

(defn component []
  (let [filter (re-frame/subscribe [:filter])
        show-details (re-frame/subscribe [:show-details])
        tasks (re-frame/subscribe [:tasks-sorted])]
    (fn component-render []
      [render @tasks
       {:filter @filter
        :on-edit #(re-frame/dispatch [:nav routes/edit {:id (:id %)}])
        :on-filter (utils/debounce (fn [filter] (re-frame/dispatch [:filter-update filter])) 250)
        :on-update #(re-frame/dispatch [:tasks-update %])
        :show-details @show-details
        :toggle-details #(re-frame/dispatch [:toggle-details %])}])))

;; )
