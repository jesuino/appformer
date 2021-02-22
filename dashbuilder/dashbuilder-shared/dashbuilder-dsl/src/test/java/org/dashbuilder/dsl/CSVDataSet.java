package org.dashbuilder.dsl;

import java.util.Arrays;
import java.util.Date;

import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dataset.def.DataSetDefFactory;
import org.dashbuilder.dataset.group.AggregateFunctionType;
import org.dashbuilder.dataset.sort.SortOrder;
import org.dashbuilder.displayer.DisplayerSettings;
import org.dashbuilder.displayer.DisplayerSettingsFactory;
import org.dashbuilder.displayer.Position;
import org.dashbuilder.dsl.model.Page;
import org.dashbuilder.dsl.serialization.DashboardExporter;
import org.dashbuilder.dsl.serialization.DashboardExporter.ExportType;
import org.junit.Test;

import static org.dashbuilder.dataset.DataSetLookupFactory.newDataSetLookupBuilder;
import static org.dashbuilder.displayer.DisplayerSettingsFactory.newTableSettings;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.displayer;
import static org.dashbuilder.dsl.factory.component.ComponentFactory.html;
import static org.dashbuilder.dsl.factory.dashboard.DashboardFactory.dashboard;
import static org.dashbuilder.dsl.factory.page.PageFactory.column;
import static org.dashbuilder.dsl.factory.page.PageFactory.page;
import static org.dashbuilder.dsl.factory.page.PageFactory.row;

public class CSVDataSet {

    private static final String CSV_URL = "https://raw.githubusercontent.com/jesuino/dashbuilder-data/master/pokemon/pokemon.csv";

    @Test
    public void test() throws Exception {

        DataSetDef pokemonDs = DataSetDefFactory.newCSVDataSetDef()
                                                .fileURL(CSV_URL)
                                                .separatorChar(',')
                                                .quoteChar('"')
                                                .buildDef();

        DisplayerSettings tableSettings = newTableSettings().tableColumnPickerEnabled(false)
                                                            .buildSettings();
        DisplayerSettings pieChart = DisplayerSettingsFactory.newPieChartSettings()
                                                             .legendOn(Position.RIGHT)
                                                             .buildSettings();

        tableSettings.setDataSetLookup(newDataSetLookupBuilder().dataset(pokemonDs.getUUID())
                                                                .column("name")
                                                                .column("attack")
                                                                .column("defense")
                                                                .column("hp")
                                                                .column("speed")
                                                                .column("type1")
                                                                .column("type2")
                                                                .column("generation")
                                                                .sort("attack", SortOrder.DESCENDING)
                                                                .buildLookup());

        pieChart.setDataSetLookup(newDataSetLookupBuilder().dataset(pokemonDs.getUUID())
                                                           .group("type1")
                                                           .column("type1")
                                                           .column("type1",
                                                                   AggregateFunctionType.COUNT)
                                                           .buildLookup());

        Page page = page("Pokemons Example",
                         row("<h1> Pokemons</h1>"),
                         row("<hr />"),
                         row("<h3>Pokemon by type</h3>"),
                         row(displayer(pieChart)),
                         row("<hr />"),
                         row("<h3>All Pokemons</h3>"),
                         row(displayer(tableSettings)),
                         row(column(html("<em> Generated at " + new Date() + "</em>"))));

        DashboardExporter.export(dashboard(Arrays.asList(page),
                                           Arrays.asList(pokemonDs)),
                                 "/tmp/dashbuilder/models/pokemons_sample.zip",
                                 ExportType.ZIP);

    }
}