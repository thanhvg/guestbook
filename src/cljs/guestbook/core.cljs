(ns guestbook.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]))


;; (defn mount-components []
;;   (let [content (js/document.getElementById "app")]
;;     (while (.hasChildNodes content)
;;       (.removeChild content (.-lastChild content)))
;;     (.appendChild content (js/document.createTextNode "Making repl to spacemacs is hard: so i don't feel the hype at all"))))

(defn message-form []
  (let [fields (atom {})]
    (fn []
      [:div.content
       [:div.form-group
        [:p "name: " (:name @fields)]
        [:p "message: " (:message @fields)]
        [:p "Name: "
         [:input.form-control
          {:type :text
           :name :name
           :on-change #(swap! fields assoc :name (-> % .-target .-value))
           :value (:name @fields)}]]]
       [:p "Message:"
        [:textarea.form-control
         {:rows 4
          :cols 50
          :name :message
          :on-change #(swap! fields assoc :message (-> % .-target .-value))}
         (:message @fields)]]
       [:input.btn.btn-primary {:type :submit :value "comment"}]])))

(defn home []
    [message-form])

(defn mount-components []
  (reagent/render
   [home]
   (js/document.getElementById "app")))

(defn init! []
  (mount-components))

