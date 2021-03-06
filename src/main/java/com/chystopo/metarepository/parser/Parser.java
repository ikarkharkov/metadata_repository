package com.chystopo.metarepository.parser;

import com.chystopo.metarepository.IParser;
import com.chystopo.metarepository.bean.Mapping;
import com.chystopo.metarepository.bean.Model;
import com.chystopo.metarepository.parser.model.MappingBean;
import com.chystopo.metarepository.parser.model.ModelBean;
import com.chystopo.metarepository.parser.model.RepositoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class Parser implements IParser {
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);

    @Override
    public Repo parse(String context, InputStream inputStream) {
        Parser jaxbConverter = new Parser();
        RepositoryBean repository;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RepositoryBean.class);
            repository = jaxbConverter.xml2Pojo(inputStream, jaxbContext);
        } catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage());
        }

        List<Model> models = new ArrayList<Model>();
        BeanVisitor visitor = new BeanVisitor(context, repository.getModels());
        for (ModelBean modelBean : repository.getModels()) {
            models.add(modelBean.toEntity(visitor, null));
        }

        List<Mapping> mappings = new ArrayList<Mapping>();

        for (MappingBean mappingBean : repository.getMappings()) {
            mappings.add(mappingBean.toEntity(visitor, null));
        }
        return new Repo(models, mappings);
    }

    private String pojo2Xml(Object object, JAXBContext context)

            throws JAXBException {

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);
        return writer.toString();
    }

    private RepositoryBean xml2Pojo(InputStream xmlStringData, JAXBContext context)

            throws JAXBException {
        Reader reader = new BufferedReader(new InputStreamReader(xmlStringData));
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (RepositoryBean) unmarshaller.unmarshal(reader);
    }

    @SuppressWarnings("unchecked")
    public static void main(String args[]) {

    }
}
