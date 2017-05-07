(ns tasks.components.tasks-list.create
  (:require [re-frame.core :as re-frame]
            [tasks.routes :as routes]))

(defn render [{:keys [on-create]}]
  [:button.tasks-create
   {:type "button"
    :on-click on-create}
   "+"])

(defn component []
  (let [on-create #(re-frame/dispatch [:nav routes/task-create])]
    (fn []
      [render {:on-create on-create}])))
