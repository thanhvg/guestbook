(ns guestbook.core)

(defn mount-components []
  (let [content (js/document.getElementById "app")]
    (while (.hasChildNodes content)
      (.removeChild content (.-lastChild content)))
    (.appendChild content (js/document.createTextNode "Making repl to spacemacs is hard"))))

(defn init! []
  (mount-components))
