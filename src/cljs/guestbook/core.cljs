(ns guestbook.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]))


;; (defn mount-components []
;;   (let [content (js/document.getElementById "app")]
;;     (while (.hasChildNodes content)
;;       (.removeChild content (.-lastChild content)))
;;     (.appendChild content (js/document.createTextNode "Making repl to spacemacs is hard: so i don't feel the hype at all"))))

(defn send-message! [fields errors]
  (POST "/add-message"
        {:params @fields
         :format :json
         :headers {"Accept" "application/transit+json"
                   "x-csrf-token" (.-csrfToken js/window)}
         :handler #(.log js/console (str "response:" %))
         :error-handler #(do (.error js/console (str "error:" %))
                             (reset! errors (get-in % [:response :errors])))}))

(defn errors-component [errors id]
  (when-let [error (id @errors)]
    [:div.alert.alert-danger (clojure.string/join error)]))

(defn message-form []
  (let [fields (atom {})
        errors (atom nil)]
    (fn []
      [:div.content
       [errors-component errors :server-error]
       [:div.form-group
        [errors-component errors :name]
        [:p "name: " (:name @fields)]
        [:p "message: " (:message @fields)]
        [:p "Name: "
         [:input.form-control
          {:type :text
           :name :name
           :on-change #(swap! fields assoc :name (-> % .-target .-value))
           :value (:name @fields)}]]]
       [errors-component errors :message]
       [:p "Message:"
        [:textarea.form-control
         {:rows 4
          :cols 50
          :name :message
          :on-change #(swap! fields assoc :message (-> % .-target .-value))}
         (:message @fields)]]
       [:input.btn.btn-primary
        {:type :submit
         :on-click #(send-message! fields errors)
         :value "comment"}]])))

(defn home []
  [message-form])

(defn mount-components []
  (reagent/render
   [home]
   (js/document.getElementById "app")))

(defn init! []
  (mount-components))
