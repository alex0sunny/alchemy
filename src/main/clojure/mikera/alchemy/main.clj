(ns mikera.alchemy.main
  (:require [mikera.orculje.gui :as gui])
  (:import [javax.swing JFrame JComponent])
  (:import [java.awt Font Color])
  (:import [mikera.gui JConsole]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(def ^Font font (Font. "Courier New" Font/PLAIN 16))

(defn new-frame 
  (^JFrame []
    (let [frame (JFrame.)]
      frame)))

(defn new-console
  (^JConsole []
    (let [jc (JConsole. 100 40)]
      (.setMainFont jc font)
      (.setFocusable jc true)
      (.setCursorVisible jc false)
      (.setCursorBlink jc false)
      jc)))

(defn redraw-screen [state]
  (let [^JConsole jc (:console state)
        w (.getColumns jc)
        h (.getRows jc)
        gw (- w 20)
        gh (- h 5)]
    (.setBackground jc (Color. 0x203040))
    (dotimes [y gh]
      (dotimes [x gw]
        (gui/draw jc x y (str x))))
    (.repaint jc)))

(defn launch 
  "Launch the game with an initial game state. Can be called from REPL."
  ([state]
    (let [^JFrame frame (:frame state)
          ^JConsole jc (:console state)]
      (.add (.getContentPane frame) jc)
      (.pack frame)
      (.setVisible frame true)
      (.write jc "Alchemy Lives!") 
      (redraw-screen state) 
      frame)))

(defn new-state
  "Create a brand new game state."
  ([]
    (let [state {:game (atom nil)
                 :console (new-console)
                 :frame (new-frame)
                 :event-handler (atom nil)}]
      state)))

;; a state for the world
(def s (new-state))


(defn main 
  "Main entry point to the demo, called directly from Java main() method in DemoApp"
  ([]
    (let [^JFrame frame (launch s)]
      (.setDefaultCloseOperation frame JFrame/EXIT_ON_CLOSE))))