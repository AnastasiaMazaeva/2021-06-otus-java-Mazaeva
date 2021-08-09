package agent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class CustomClassVisitor extends ClassVisitor {

    private final int version;

    public CustomClassVisitor(int version, ClassWriter classWriter) {
        super(version, classWriter);
        this.version = version;
    }

    @Override
    public MethodVisitor visitMethod(int access,
                                     String name,
                                     String descriptor,
                                     String signature,
                                     String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new CustomMethodVisitor(version, mv, name, descriptor);
    }

}
