#!/usr/bin/env python
import os

from gi.repository import Gtk

gladefile = os.path.join(os.path.dirname(__file__), "template-gui.glade")


class firstWindow(object):

    def __init__(self):
        self.builder = Gtk.Builder()
        self.builder.add_from_file(gladefile)
        self.firstWin = self.builder.get_object("firstWindow")
        self.mainhandlers = {
            "on_cancel_clicked": Gtk.main_quit,
            "on_cancelBtn_clicked": Gtk.main_quit,
            "on_firstWindow_delete_event": Gtk.main_quit,
        }
        self.builder.connect_signals(self.mainhandlers)
        self.firstWin.show_all()
        Gtk.main()
