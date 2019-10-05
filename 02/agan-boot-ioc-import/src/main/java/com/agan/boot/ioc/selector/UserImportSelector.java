package com.agan.boot.ioc.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class UserImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.agan.boot.ioc.selector.UserBean","com.agan.boot.ioc.selector.RoleBean"};
    }
}
