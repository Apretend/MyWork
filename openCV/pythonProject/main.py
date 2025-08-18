import cv2
import numpy as np
from mss import mss

with mss() as sct:
    monitor = {"top": 40, "left": 0, "width": 800, "height": 640}
    output = "sct-{top}x{left}_{width}x{height}.png".format(**monitor)
    sct_img = sct.grab(monitor)
    img = np.array(sct_img)
    cv2.imshow("Screenshot", img)
    if cv2.waitKey(25000) & 0xFF == ord("q"):
        cv2.destroyAllWindows()