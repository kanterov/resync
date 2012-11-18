package ru.nsu.ccfit.resync.pref.jdt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ru.nsu.ccfit.resync.pref.Preference;
import ru.nsu.ccfit.resync.pref.PreferenceProvider;
import ru.nsu.ccfit.resync.storage.PreferenceStorage;

public class SpCleanup implements PreferenceProvider {

    private static final String BUNDLE = "org.eclipse.jdt.ui";
    private static final String DELIMETER = "/";
    private static final String PREFIX = BUNDLE + DELIMETER;

    // @formatter:off
    private static final List<String> OPTIONS = Arrays.asList(
            PREFIX + "sp_cleanup.add_default_serial_version_id",
            PREFIX + "sp_cleanup.add_generated_serial_version_id",
            PREFIX + "sp_cleanup.add_missing_annotations",
            PREFIX + "sp_cleanup.add_missing_deprecated_annotations",
            PREFIX + "sp_cleanup.add_missing_methods",
            PREFIX + "sp_cleanup.add_missing_nls_tags",
            PREFIX + "sp_cleanup.add_missing_override_annotations",
            PREFIX + "sp_cleanup.add_missing_override_annotations_interface_methods",
            PREFIX + "sp_cleanup.add_serial_version_id",
            PREFIX + "sp_cleanup.always_use_blocks",
            PREFIX + "sp_cleanup.always_use_parentheses_in_expressions",
            PREFIX + "sp_cleanup.always_use_this_for_non_static_field_access",
            PREFIX + "sp_cleanup.always_use_this_for_non_static_method_access",
            PREFIX + "sp_cleanup.convert_to_enhanced_for_loop",
            PREFIX + "sp_cleanup.correct_indentation",
            PREFIX + "sp_cleanup.format_source_code",
            PREFIX + "sp_cleanup.format_source_code_changes_only",
            PREFIX + "sp_cleanup.make_local_variable_final",
            PREFIX + "sp_cleanup.make_parameters_final",
            PREFIX + "sp_cleanup.make_private_fields_final",
            PREFIX + "sp_cleanup.make_type_abstract_if_missing_method",
            PREFIX + "sp_cleanup.make_variable_declarations_final",
            PREFIX + "sp_cleanup.never_use_blocks",
            PREFIX + "sp_cleanup.never_use_parentheses_in_expressions",
            PREFIX + "sp_cleanup.on_save_use_additional_actions",
            PREFIX + "sp_cleanup.organize_imports",
            PREFIX + "sp_cleanup.qualify_static_field_accesses_with_declaring_class",
            PREFIX + "sp_cleanup.qualify_static_member_accesses_through_instances_with_declaring_class",
            PREFIX + "sp_cleanup.qualify_static_member_accesses_through_subtypes_with_declaring_class",
            PREFIX + "sp_cleanup.qualify_static_member_accesses_with_declaring_class",
            PREFIX + "sp_cleanup.qualify_static_method_accesses_with_declaring_class",
            PREFIX + "sp_cleanup.remove_private_constructors",
            PREFIX + "sp_cleanup.remove_trailing_whitespaces",
            PREFIX + "sp_cleanup.remove_trailing_whitespaces_all",
            PREFIX + "sp_cleanup.remove_trailing_whitespaces_ignore_empty",
            PREFIX + "sp_cleanup.remove_unnecessary_casts",
            PREFIX + "sp_cleanup.remove_unnecessary_nls_tags",
            PREFIX + "sp_cleanup.remove_unused_imports",
            PREFIX + "sp_cleanup.remove_unused_local_variables",
            PREFIX + "sp_cleanup.remove_unused_private_fields",
            PREFIX + "sp_cleanup.remove_unused_private_members",
            PREFIX + "sp_cleanup.remove_unused_private_methods",
            PREFIX + "sp_cleanup.remove_unused_private_types",
            PREFIX + "sp_cleanup.sort_members",
            PREFIX + "sp_cleanup.sort_members_all",
            PREFIX + "sp_cleanup.use_blocks",
            PREFIX + "sp_cleanup.use_blocks_only_for_return_and_throw",
            PREFIX + "sp_cleanup.use_parentheses_in_expressions",
            PREFIX + "sp_cleanup.use_this_for_non_static_field_access",
            PREFIX + "sp_cleanup.use_this_for_non_static_field_access_only_if_necessary",
            PREFIX + "sp_cleanup.use_this_for_non_static_method_access",
            PREFIX + "sp_cleanup.use_this_for_non_static_method_access_only_if_necessary");
    // @formatter:on

    @Override
    public Collection<Preference> getPreferences(PreferenceStorage storage) {
        List<Preference> preferences = new ArrayList<Preference>();

        for (String option : OPTIONS) {
            String value = storage.get(option, null);
            String key = option.substring(PREFIX.length());

            if (value != null) {
                preferences.add(Preference.newInstance(BUNDLE, key, value));
            }
        }

        return preferences;
    }

    @Override
    public Collection<Preference> exportPreferences() {
        // TODO: implement this
        return Collections.emptyList();
    }

}
