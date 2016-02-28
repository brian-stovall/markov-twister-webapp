(ns compojure-test.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.anti-forgery :as r-a-f]
            [hiccup.page :as hic-p]
            [hiccup.element :as hic-e]
            [hiccup.form :as hic-f]
            [clojure.java.io :as io]))

(load "generator")
(use 'markov-twister.generator)
 
(defroutes app-routes
  
  (GET "/" []
       (hic-p/html5
        [:head  (hic-p/include-css "/css/library.css")]
        [:div {:class "container"}
         [:h1 "Once Upon a Tornado..."]
         [:h3 "Choose stories from the library:"]
         (hic-f/form-to [:post "/my-story"]
                        [:div 
                         (hic-f/check-box :alice false "sample-text/alice.txt")
                         (hic-f/label :alice "Alice in Wonderland")]
                        [:div
                         (hic-f/check-box :sherlock false "sample-text/sherlock.txt")
                         (hic-f/label :sherlock "The Adventures of Sherlock Holmes")
                         ]
                        [:div
                         (hic-f/check-box :conneticut false "sample-text/conneticut.txt")
                         (hic-f/label :conneticut "A Conneticut Yankee in King Arthur's Court")]
                        [:div
                         (hic-f/check-box :critique false "sample-text/critique.txt")
                         (hic-f/label :critique "A Critique of Pure Reason")]
                        [:div
                         (hic-f/check-box :divine false "sample-text/divine.txt")
                         (hic-f/label :divine "The Divine Comedy")]
                        [:div
                         (hic-f/check-box :dunwich false "sample-text/dunwich.txt")
                         (hic-f/label :dunwich "The Dunwich Horror")]
                        [:div
                         (hic-f/check-box :ethics false "sample-text/ethics.txt")
                         (hic-f/label :ethics "Ethics")]
                        [:div
                         (hic-f/check-box :grimm false "sample-text/grimm.txt")
                         (hic-f/label :grimm "Grimm Fairytales")]
                        [:div
                         (hic-f/check-box :macbeth false "sample-text/macbeth.txt")
                         (hic-f/label :macbeth "Macbeth")]
                        [:div
                         (hic-f/check-box :man false "sample-text/man.txt")
                         (hic-f/label :man "The Man With Two Left Feet")]
                        [:div
                         (hic-f/check-box :munch false "sample-text/munchausen.txt")
                         (hic-f/label :munch "Mr. Munchausen")]
                        [:div
                         (hic-f/check-box :mysticism false "sample-text/mysticism.txt")
                         (hic-f/label :mysticism "Mysticism and Logic")]
                        [:div
                         (hic-f/check-box :nonsense false "sample-text/nonsense.txt")
                         (hic-f/label :nonsense "Nonsense Books")]
                        [:div
                         (hic-f/check-box :paradise false "sample-text/paradise.txt")
                         (hic-f/label :paradise "Paradise Lost")]
                        [:div
                         (hic-f/check-box :poe false "sample-text/poe.txt")
                         (hic-f/label :poe "The Works of Edgar Allen Poe: Vol. One")]
                        [:div
                         (hic-f/check-box :pride false "sample-text/pride.txt")
                         (hic-f/label :pride "Pride and Prejudice")]
                        [:div
                         (hic-f/check-box :qix false "sample-text/qix.txt")
                         (hic-f/label :qix "Don Quixote")]
                        [:div
                         (hic-f/check-box :tale false "sample-text/tale.txt")
                         (hic-f/label :tale "A Tale of Two Cities")]
                        [:div
                         (hic-f/check-box :trial false "sample-text/trial.txt")
                         (hic-f/label :trial "The Trial")]
                        [:div
                         (hic-f/check-box :white false "sample-text/white.txt")
                         (hic-f/label :white "White Nights")]
                        
                        (r-a-f/anti-forgery-field)
                        (hic-f/submit-button "Write it!"))]))

  (POST "/my-story"
        req
        (hic-p/html5
         [:head  (hic-p/include-css "/css/story.css")]
         [:pre [:p (apply files->story
                          500
                          (vals (select-keys (:params req)
                                             [:alice :sherlock :conneticut :critique
                                              :divine :qix :dunwich :ethics :grimm
                                              :macbeth :man :munch :mysticism :nonsense
                                              :paradise :poe :pride :qix :tale
                                              :trial :white])))]]))
  
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
