module filerenamer.swing {
    requires eventbus;
    requires java.desktop;
    requires kotlin.stdlib;
    exports ch.frankel.blog.renamer to eventbus;
}